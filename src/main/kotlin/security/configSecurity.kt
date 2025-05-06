package com.example.security

import com.example.config.jwtVerifier
import com.example.domain.services.UserService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt

fun  Application.configSecurity(userService: UserService) {
    /*val jwtSecret = environment.config.property("jwt.jwt_secret").getString()
    val jwtIssuer = environment.config.property("jwt.issuer").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()*/
    val claimField = environment.config.property("jwt.claimField").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

    val verifier = jwtVerifier()
    println("url verifier $verifier")

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(verifier)
            validate { cred ->
                cred.payload.getClaim(
                    claimField
                ).asInt()?.let {
                    userService.getUserById(it)
                }
            }
        }
    }
}