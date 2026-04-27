package com.example.faculty_app.auth.infrastructure.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.faculty_app.auth.data.remote.models.response.Tokens;
import com.example.faculty_app.auth.data.repositories.AuthRepository;
import com.example.faculty_app.auth.data.local.SessionManager;
import com.example.faculty_app.shared.RepositoryResult;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private final SessionManager sessionManager;

    public TokenAuthenticator() {
        sessionManager = SessionManager.getInstance();
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route,
                                @NonNull Response response) throws IOException {
        synchronized (this) {
            if (!isMarkedForInjection(response.request()))
                return null;

            var currentAccessToken = sessionManager.getAccessToken();
            var refreshToken = sessionManager.getRefreshToken();

            var header = response.request().header("Authorization");
            var headerToken = header != null ? header.split(" ")[1] : null;

            if (currentAccessToken != null && !currentAccessToken.equals(headerToken)) {
                return buildNewRequest(response, currentAccessToken);
            }

            if (refreshToken == null || refreshToken.isBlank())
                return clearSession();

            var result = AuthRepository.getInstance().refresh();

            if (result instanceof RepositoryResult.Success) {
                var tokens = ((RepositoryResult.Success<Tokens>) result).getData();
                return buildNewRequest(response, tokens.accessToken);
            }
            return null;
        }
    }

    private boolean isMarkedForInjection(Request request) {
        return request.header("X-Inject-Auth") != null;
    }

    private Request clearSession() {
        sessionManager.clear();
        return null;
    }

    private Request buildNewRequest(Response response, String token) {
        return response.request().newBuilder().header("Authorization", "Bearer " + token).build();
    }

}
