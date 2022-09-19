package com.model.rest;

import com.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConsumeWebService {

    private static RestTemplate restTemplate = new RestTemplate();
    private static String requestURL = "http://94.198.50.185:7081/api/users/";
    private static User user = new User();
    public static void getCode(){
        String cookies = getAllUsers();
        System.out.println(cookies);

        String code = addUser(cookies) + updateUser(cookies) + deleteUser(3L, cookies);
        System.out.println(code);
    }
    public static String getAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        restTemplate.exchange(requestURL, HttpMethod.GET, entity,String.class);

        return restTemplate.exchange(requestURL, HttpMethod.GET, entity,String.class).getHeaders()
                .get("Set-Cookie")
                .stream()
                .collect(Collectors.joining(";"));
    }

    public static String addUser(String cookies){
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte)22);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie",cookies);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        return restTemplate.exchange(requestURL, HttpMethod.POST,entity,String.class).getBody();
    }

    public static String updateUser(String cookies){
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookies);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        return restTemplate.exchange(requestURL, HttpMethod.PUT, entity,String.class).getBody();
    }

    public static String deleteUser(Long id, String cookie){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        return restTemplate.exchange(requestURL + id.toString(), HttpMethod.DELETE, entity,String.class).getBody();
    }


}