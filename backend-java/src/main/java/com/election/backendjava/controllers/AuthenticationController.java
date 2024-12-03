import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @PostMapping(path = "/login")
    public ResponseEntity<Account> authenticateAccount(@RequestBody ObjectNode signInInfo) {

        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        List<Account> accounts = accountsRepo.findByQuery("Accounts_find_by_email", email);
        Account account = accounts.size() == 1 ? accounts.get(0) : null;

        if (account != null || !account.verfiyPassword(password)) {
            throw new NotAcceptableException("Cannot authenticate account with email=" + email);
        }

        // Issue a token for the account, valid for some time​
        JWToken jwToken = new JWToken(account.getCallName(), account.getId());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),  this.apiConfig.getTokenDurationOfValidity());

        return ResponseEntity.accepted()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(account);
    }

    @DeleteMapping(path = "{id}")
    public Account deleteOneAccount(@PathVariable() long id,
                                    @RequestAttribute(name = JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtInfo) {

        if (jwtInfo == null || !jwtInfo.isAdmin()) {
            throw new UnAuthorizedException(
                    "Admin role is required to remove an account");
        }
        Account account = this.accountsRepo.deleteById(id);

        if (account == null) {
            throw new ResourceNotFoundException(
                    "Cannot delete an account with id=" + id);
        }

        return account;
    }
}