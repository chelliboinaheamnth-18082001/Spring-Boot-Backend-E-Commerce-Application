package com.example.user_service.Services.KeyclockService;

import com.example.user_service.User_DTOs.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeyClockUserService {

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @Value("${keycloak.admin.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.admin.realm}")
    private String realm;

    // For token generation
    @Value("${keycloak.admin.client-id}")
    private String clientId;

    // IMPORTANT → internal UUID of client
    @Value("${keycloak.admin.client-uid}")
    private String clientUid;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔑 STEP 1: Get Admin Token
    public String getAdminAccessToken() {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("username", adminUsername);
        params.add("password", adminPassword);
        params.add("grant_type", "password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(params, headers);

        String url = keycloakServerUrl +
                "/realms/" + realm +
                "/protocol/openid-connect/token";

        ResponseEntity<Map> response = restTemplate.postForEntity(
                url,
                entity,
                Map.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to get admin token");
        }

        return (String) response.getBody().get("access_token");
    }

    // 👤 STEP 2: Create User
    public String createUser(String token, UserRequestDto userRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", userRequest.getUsername());
        userPayload.put("email", userRequest.getEmail());
        userPayload.put("enabled", true);
        userPayload.put("firstName", userRequest.getFirstName());
        userPayload.put("lastName", userRequest.getLastName());

        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", userRequest.getPassword());
        credential.put("temporary", false);

        userPayload.put("credentials", List.of(credential));

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(userPayload, headers);

        String url = keycloakServerUrl +
                "/admin/realms/" + realm + "/users";

        ResponseEntity<String> response = restTemplate.postForEntity(
                url,
                entity,
                String.class
        );

        if (!HttpStatus.CREATED.equals(response.getStatusCode())) {
            throw new RuntimeException(
                    "Failed to create user in Keycloak: " + response.getBody()
            );
        }

        URI location = response.getHeaders().getLocation();

        if (location == null) {
            throw new RuntimeException("Keycloak did not return Location header");
        }

        String path = location.getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }

    // 🎭 STEP 3: Get CLIENT Role
    private Map<String, Object> getClientRoleRepresentation(
            String token,
            String roleName
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = keycloakServerUrl +
                "/admin/realms/" + realm +
                "/clients/" + clientUid +
                "/roles/" + roleName;

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        if (!response.getStatusCode().is2xxSuccessful()
                || response.getBody() == null) {
            throw new RuntimeException(
                    "Client role not found in Keycloak: " + roleName
            );
        }

        return response.getBody();
    }

    // 🔗 STEP 4: Assign CLIENT Role (🔥 FIXED)
    public void assignClientRoleToUser(
            String token,
            String username,
            String roleName,
            String userId
    ) {

        try {
            Map<String, Object> roleRepFull =
                    getClientRoleRepresentation(token, roleName);

            // ✅ IMPORTANT FIX → send only id + name
            Map<String, Object> roleRep = new HashMap<>();
            roleRep.put("id", roleRepFull.get("id"));
            roleRep.put("name", roleRepFull.get("name"));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(token);

            HttpEntity<List<Map<String, Object>>> entity =
                    new HttpEntity<>(List.of(roleRep), headers);

            String url = keycloakServerUrl +
                    "/admin/realms/" + realm +
                    "/users/" + userId +
                    "/role-mappings/clients/" + clientUid;

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Void.class
            );

            System.out.println("Assign Role Status: " + response.getStatusCode());

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException(
                        "Failed to assign role " + roleName +
                                " to user " + username +
                                ": HTTP " + response.getStatusCode()
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Role assignment failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Role assignment failed", e);
        }
    }
}