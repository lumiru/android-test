package in.turp.persistancetp.service;

import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by lumiru on 08/07/15.
 */
public interface AuthService {
    @POST("/commercial/api-token-auth/")
    AccessToken login(@Body Utilisateur body);

    class Utilisateur {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Utilisateur(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    class AccessToken {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
