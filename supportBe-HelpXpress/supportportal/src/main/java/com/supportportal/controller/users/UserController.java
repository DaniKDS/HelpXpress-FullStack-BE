package com.supportportal.controller.users;

import com.supportportal.domain.*;
import com.supportportal.domain.Http.HttpResponse;
import com.supportportal.domain.principal.UserPrincipal;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.*;
import com.supportportal.service.inter.UserService;
import com.supportportal.service.users.AssistantService;
import com.supportportal.service.users.DoctorService;
import com.supportportal.service.users.SpecialUserService;
import com.supportportal.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static com.supportportal.constant.FileConstant.*;
import static com.supportportal.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping(path = { "/", "/user"})
public class UserController extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AssistantService assistantService;
    private final DoctorService doctorService;
    private final SpecialUserService specialUserService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, AssistantService assistantService, DoctorService doctorService, SpecialUserService specialPersonService, SpecialUserService specialUserService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.assistantService = assistantService;
        this.doctorService = doctorService;
        this.specialUserService = specialUserService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("role") String role,
                                           @RequestParam("isActive") String isActive,
                                           @RequestParam("isNonLocked") String isNonLocked,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User newUser = userService.addNewUser(firstName, lastName, username,email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("gender") String gender,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("role") String role,
                                       @RequestParam("isActive") String isActive,
                                       @RequestParam("isNonLocked") String isNonLocked,
                                       @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User updatedUser;
        updatedUser = userService.updateUser(currentUsername, firstName, lastName, username,email,gender,phone, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(updatedUser, OK);
    }
    
    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/assistant")
    public ResponseEntity<List<Assistant>> getAllAssistants() {
        List<Assistant> assistants = assistantService.findAllAssistants();
        return new ResponseEntity<>(assistants, HttpStatus.OK);
    }

    @GetMapping("/assistant/{assistantId}")
    public ResponseEntity<Assistant> getAssistant(@PathVariable("assistantId") Long assistantId) {
        Assistant assistant = assistantService.findAssistantById(assistantId);
        return new ResponseEntity<>(assistant, HttpStatus.OK);
    }

    @GetMapping("/doctor")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.findAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable("doctorId") Long doctorId) {
        Doctor doctor = doctorService.findDoctorById(doctorId);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping("/specialPerson")
    public ResponseEntity<List<SpecialUser>> getAllSpecialPersons() {
        List<SpecialUser> specialPersons = specialUserService.findAllSpecialUsers();
        return new ResponseEntity<>(specialPersons, HttpStatus.OK);
    }

    @GetMapping("/specialPerson/{specialUserId}")
    public ResponseEntity<SpecialUser> getSpecialPerson(@PathVariable("specialUserId") Long specialUserId) {
        SpecialUser specialPerson = specialUserService.findSpecialUserById(specialUserId);
        return new ResponseEntity<>(specialPerson, HttpStatus.OK);
    }

    @GetMapping("/resetpassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws MessagingException, EmailNotFoundException {
        userService.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
        userService.deleteUser(username);
        return response(OK, USER_DELETED_SUCCESSFULLY);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username, @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user = userService.updateProfileImage(username, profileImage);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(TEMP_PROFILE_IMAGE_BASE_URL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/addManyUsers")
    public ResponseEntity<HttpResponse> addManyUsers() {
        try {
            userService.saveManyUsers();
            return response(OK, "80 de utilizatori au fost adăugați cu succes.");
        } catch (Exception e) {
            LOGGER.error("Eroare la adăugarea utilizatorilor: ", e);
            return response(INTERNAL_SERVER_ERROR, "A apărut o eroare la adăugarea utilizatorilor.");
        }
    }
    @DeleteMapping("/deleteLast100Users")
    // @PreAuthorize("hasAuthority('role:admin')") // Activează această linie dacă securitatea este configurată
    public ResponseEntity<String> deleteLast100Users() {
        try {
            userService.deleteLast100Users();
            return ResponseEntity.ok("Ultimii 100 de utilizatori au fost șterși cu succes.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A apărut o eroare la ștergerea utilizatorilor.");
        }
    }

    @PostMapping("/addManyAssistants")
    public ResponseEntity<HttpResponse> addManyAssistants() {
        try {
            assistantService.saveManyAssistants();
            return response(OK, "Asistenții au fost adăugați cu succes.");
        } catch (Exception e) {
            LOGGER.error("Eroare la adăugarea asistenților: ", e);
            return response(INTERNAL_SERVER_ERROR, "A apărut o eroare la adăugarea asistenților.");
        }
    }

    @PostMapping("/addManyDoctors")
    public ResponseEntity<HttpResponse> addManyDoctors() {
        try {
            doctorService.saveManyDoctors();
            return response(OK, "Doctorii au fost adăugați cu succes.");
        } catch (Exception e) {
            LOGGER.error("Eroare la adăugarea doctorilor: ", e);
            return response(INTERNAL_SERVER_ERROR, "A apărut o eroare la adăugarea doctorilor.");
        }
    }

    @PostMapping("/addManySpecialUsers")
    public ResponseEntity<HttpResponse> addManySpecialUsers() {
        try {
            specialUserService.saveManySpecialUsers();
            return response(OK, "Utilizatorii speciali au fost adăugați cu succes.");
        } catch (Exception e) {
            LOGGER.error("Eroare la adăugarea utilizatorilor speciali: ", e);
            return response(INTERNAL_SERVER_ERROR, "A apărut o eroare la adăugarea utilizatorilor speciali.");
        }
    }

    @GetMapping("/special-users/by-username/{username}/doctors")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialUserUsername(@PathVariable String username) {
        List<Doctor> doctors = specialUserService.findDoctorsBySpecialUserUsername(username);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/special-users/{username}/assistant")
    public ResponseEntity<Assistant> getAssistantBySpecialUserUsername(@PathVariable String username) {
        try {
            Assistant assistant = specialUserService.findAssistantBySpecialUserUsername(username);
            return new ResponseEntity<>(assistant, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
