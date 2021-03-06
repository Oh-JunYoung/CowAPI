//package server.server.Service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.server.ResponseStatusException;
//import server.server.Domain.Dto.*;
//import server.server.Domain.Entity.UserState;
//import server.server.Domain.Repository.UsersRepository;
//import server.server.Redis.StateRedisService;
//
//import java.math.BigInteger;
//import java.security.SecureRandom;
//
//@Service
//@RequiredArgsConstructor
//public class OAuthUserService {
//    private final UserService userService;
//    private final StateRedisService stateRedisService;
//    private final UsersRepository usersRepository;
//
//    @Value(value = "${spring.security.oauth2.client.registration.naver.client-id}")
//    String clientId;
//
//    @Value(value = "${spring.security.oauth2.client.registration.naver.client-secret}")
//    String clientSecret;
//
//    @Value(value = "${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
//    String grandType;
//
//    @Value(value = "${spring.security.oauth2.client.registration.naver.scope}")
//    String scope;
//
//    @Value(value = "${spring.security.oauth2.client.registration.naver.client-name}")
//    String clientName;
//
//    @Value(value = "${spring.security.oauth2.client.registration.naver.redirect-uri}")
//    String redirectUri;
//
//    @Value(value = "${spring.security.oauth2.client.provider.naver.authorization-uri}")
//    String authorizationUri;
//
//    @Value(value = "${spring.security.oauth2.client.provider.naver.token-uri}")
//    String tokenUri;
//
//    @Value(value = "${spring.security.oauth2.client.provider.naver.user-info-uri}")
//    String userInfoUri;
//
//    @Value(value = "${spring.security.oauth2.client.provider.naver.user-name-attribute}")
//    String userName;
//
//    @Value(value = "${AWS.ip}")
//    String AWSip;
//
//    public String generateState() {
//        SecureRandom random = new SecureRandom();
//        return new BigInteger(130, random).toString(32);
//    }
//
//    public RedirectURIDto redirectURI() {
//        // state ??????
//        UserState state = UserState.builder()
//                .state(generateState())
//                .valid(true)
//                .build();
//
//        // Redis ??? state ??????
//        stateRedisService.save(state);
//
//        // redirect uri ??????
//        return RedirectURIDto.builder()
//                .redirectURI(generateRedirectURI(state.getState()))
//                .build();
//    }
//
//    public String generateRedirectURI(String state) {
////        https://nid.naver.com/oauth2.0/authorize?client_id={??????????????? ?????????}&response_type=code&redirect_uri={????????? ????????? ????????? ?????? URL(URL ?????????)}&state={?????? ??????}
//        return authorizationUri + "?client_id=" + clientId + "&response_type=code&redirect_uri=" + redirectUri + "&state=" + state;
//    }
//
//    public UsersDto OauthUserSignIn(String code, String state) {
//
//        UserState userState = UserState.builder().state(state).build();
//
//        // state ??????
//        if(stateRedisService.get(userState) == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "?????? ????????? ???????????? ????????????.");
//        if(!stateRedisService.get(userState).getValid()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "?????? ????????? ???????????? ????????????.");
//
//        // Authorization Server ??? ????????? WebClient
//        WebClient client = WebClient.create("http://localhost:8080");
//
//        // ?????? ???????????? ?????? ??????
//        OAuthTokenDto accessToken = client.get()
//                .uri(generateTokenURI(state, code))
//                .retrieve()
//                .bodyToMono(OAuthTokenDto.class)
//                .block();
//
//        // state ??????
//        stateRedisService.delete(userState);
//
//        if(accessToken == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization Server Error");
//
//        // User Info ?????? ?????? ??????
//        OAuthUserInfoDto userInfo = client.post()
//                .uri(userInfoUri)
//                .header(HttpHeaders.AUTHORIZATION, generateToken(accessToken.getTokenType(), accessToken.getAccessToken()))
//                .retrieve()
//                .bodyToMono(OAuthUserInfoDto.class)
//                .block();
//
//        if(userInfo == null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Resource Service Error!");
//
////        // ????????????, ?????????
//        UsersDto usersDto = UsersDto.builder()
//                .email(userInfo.getResponse().getEmail())
//                .password(userInfo.getResponse().getEmail().substring(7))
//                .build();
//
//        return (!usersRepository.existsByEmail(usersDto.getEmail())) ? userService.signUp(usersDto) : userService.signIn(usersDto);
//    }
//
//    public String generateTokenURI(String state, String code) {
//        // https://nid.naver.com/oauth2.0/token?client_id={??????????????? ?????????}&client_secret={??????????????? ?????????}&grant_type=authorization_code&state={?????? ??????}&code={?????? ??????}
//        return tokenUri + "?client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=" + grandType + "&state=" + state + "&code=" + code;
//    }
//
//    public String generateToken(String type, String token) {
//        return "Bearer" + " " + token;
//    }
//}
