### Technische Documentatie: Authenticatie en JWT Functionaliteit

#### Overzicht
Deze documentatie beschrijft de implementatie van de login, registratie en JWT functionaliteit in de `AuthenticationController` klasse van de backend Java applicatie. De applicatie maakt gebruik van Spring Boot voor de backend en Vue.js voor de frontend.

#### AuthenticationController

De `AuthenticationController` klasse behandelt gebruikersregistratie en login verzoeken. Het maakt gebruik van de `AuthenticationService` voor de business logica en `JwtUtil` voor het genereren van JWT tokens.

##### Register Endpoint

- **URL**: `/api/auth/register`
- **Methode**: POST
- **Request Body**: JSON object met gebruikersgegevens (gebruikersnaam, wachtwoord, e-mail)
- **Response**: Succesbericht of foutmelding

```java
@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody User user) {
    try {
        authenticationService.register(user);
        return ResponseEntity.ok("User registered successfully!");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
    }
}
```

##### Login Endpoint

- **URL**: `/api/auth/login`
- **Methode**: POST
- **Request Body**: JSON object met gebruikersgegevens (gebruikersnaam, wachtwoord)
- **Response**: JSON object met JWT token, gebruikers-ID en gebruikersnaam of foutmelding

```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody User user) {
    try {
        String username = user.getUsername();
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is missing");
        }

        Long userid = authenticationService.login(user);
        String token = jwtUtil.generateToken(String.valueOf(userid));

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userid", String.valueOf(userid));
        response.put("username", username);

        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
    }
}
```

#### AuthenticationService

De `AuthenticationService` klasse bevat de business logica voor gebruikersregistratie en login.

##### Register Methode

- **Functie**: Registreert een nieuwe gebruiker door de gebruikersgegevens op te slaan in de database.

```java
public void register(User user) {
    // Business logica voor het registreren van een gebruiker
}
```

##### Login Methode

- **Functie**: Authenticeert de gebruiker en retourneert de gebruikers-ID indien succesvol.

```java
public Long login(User user) {
    // Business logica voor het inloggen van een gebruiker
    return userId;
}
```

#### JwtUtil

De `JwtUtil` klasse is verantwoordelijk voor het genereren en valideren van JWT tokens.

##### Generate Token Methode

- **Functie**: Genereert een JWT token voor de gegeven gebruikers-ID.

```java
public String generateToken(String userId) {
    // Logica voor het genereren van JWT token
    return token;
}
```

##### Validate Token Methode

- **Functie**: Valideert de gegeven JWT token.

```java
public boolean validateToken(String token) {
    // Logica voor het valideren van JWT token
    return isValid;
}
```

#### User Entity

De `User` klasse vertegenwoordigt de gebruikersentiteit in de database.

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role = "USER";

    // Getters en setters
}
```

Deze documentatie biedt een overzicht van de belangrijkste componenten die betrokken zijn bij de authenticatie en JWT functionaliteit van de applicatie.