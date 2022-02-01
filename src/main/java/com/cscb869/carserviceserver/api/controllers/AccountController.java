package com.cscb869.carserviceserver.api.controllers;

import com.cscb869.carserviceserver.data.entity.Account;
import com.cscb869.carserviceserver.data.entity.Role;
import com.cscb869.carserviceserver.dto.AccountLoginDTO;
import com.cscb869.carserviceserver.dto.AccountRegisterDTO;
import com.cscb869.carserviceserver.payloads.AccountDetailsResponse;
import com.cscb869.carserviceserver.payloads.JWTAuthenticationResponse;
import com.cscb869.carserviceserver.services.AccountService;
import com.cscb869.carserviceserver.services.RoleService;
import com.cscb869.carserviceserver.utils.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final AccountService accountService;
    private final RoleService roleService;

    /*@GetMapping("/")
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok().body(accountService.getAccounts());
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountDetails(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if(account != null){
            return ResponseEntity.ok(new AccountDetailsResponse(account.getFirstName(),account.getLastName(),account.getEmail()));
        }
        return new ResponseEntity<>("Account not found!", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody AccountRegisterDTO accountDTO) {
        if(accountService.isEmailTaken(accountDTO.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.CONFLICT);
        }
        if(roleService.isRoleInvalid(accountDTO.getRoleName())) {
            return new ResponseEntity<>("The given account type is invalid!", HttpStatus.BAD_REQUEST);
        }

        accountService.saveAccount(accountDTO);
        accountService.addRoleToUser(accountDTO.getEmail(),accountDTO.getRoleName());

        return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthenticationResponse> authenticateAccount(@Valid @RequestBody AccountLoginDTO accountDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                accountDTO.getEmail(),accountDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Collection<Role> roles = accountService.getAccountRoles(accountDTO.getEmail());

        // TODO: refactor and pass all user roles to token
        ArrayList<Role> roleList = new ArrayList<>(roles);
        String role = roleList.get(0).getRoleName();
        //System.out.println(role);

        Long accountId = accountService.getAccountID(accountDTO.getEmail());

        String token = jwtTokenProvider.generateToken(authentication, role, accountId);

        return ResponseEntity.ok(new JWTAuthenticationResponse(token, role));
    }


}
