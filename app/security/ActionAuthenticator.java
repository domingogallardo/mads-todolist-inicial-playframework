package security;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Controller.session;

import play.Logger;

public class ActionAuthenticator extends Security.Authenticator {
    @Override
    public String getUsername(Context ctx) {
        Logger.debug("Conectado: " + session("connected"));
        // El método session devuelve null si no hay valor y se considera no autorizado
        // llamándose al método onUnautorized (por defecto se devuelve 401 Non Authorized
        return session("connected");
    }

    @Override
    public Result onUnauthorized(Context context) {
        return unauthorized("Lo siento, no estás conectado");
    }
}
