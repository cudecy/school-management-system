package com.school.project.deleteservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.school.project.libs.common.responses.ErrorResponse;
import com.school.project.libs.common.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CustomRequestFilter implements Filter {
    private EurekaClient eurekaClient;

    private RestTemplate restTemplate;

    public CustomRequestFilter(EurekaClient eurekaClient, RestTemplate restTemplate) {
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
    }

    @Value("${auth.service.id}")
    private String authServiceId;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Application application = eurekaClient.getApplication(authServiceId);
        if(application != null) {
            InstanceInfo instanceInfo = application.getInstances().get(0);
            String url = String.format("http://%s:%s/user/get-details", instanceInfo.getIPAddr(), instanceInfo.getPort());

            HttpHeaders headers = Collections.list(req.getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            h -> Collections.list(req.getHeaders(h)),
                            (oldValue, newValue) -> newValue,
                            HttpHeaders::new
                    ));

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            try {
                ResponseEntity<SuccessResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, SuccessResponse.class);
                if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.ACCEPTED) {
                    SuccessResponse responseBody = response.getBody();
                    if (responseBody != null) {
                        if (responseBody.getResponseCode().equals(99)) {
                            // Authentication successful...
                            filterChain.doFilter(servletRequest, servletResponse);
                        }else {
                            populateServletResponse("Oops! Could not get user details at this moment", servletResponse);
                        }
                    }
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                populateServletResponse("Oops! Could not validate token integrity", servletResponse);
            }
        }else {
            populateServletResponse("Oops! Could not validate token integrity. Service Unreachable", servletResponse);
        }
    }

    private void populateServletResponse(String message, ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(new ErrorResponse(message)));
    }
}
