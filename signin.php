<?php

abstract class Session {
}

abstract class User {

    public $email;
    public $lastLoginAt;
    public $session;

    /** return `true` if the password is valid, `false` otherwise */
    abstract function checkPassword($password);

    /** returns the updated user with a new session */
    abstract function generateSession();

    /** updates the lastLoginAt date and returns the updated User */
    abstract function updateLastLogin();

    /** sets the number of login attempts, return the updated User */
    abstract function setNumLoginAttempts();
}

abstract class UserService {

    /** returns the User matching the email, otherwise returns null */
    abstract function getUserByEmail($email);
}

class RouteImpls {

    private $userService;

    /**
     * POST "/auth/signin"
     *
     * Logs a user in
     * Requirements:
     * - password must be correct
     * - can try up to 3 times
     * - updates lastloginAt date
     * - returns a new session
     *
     * @param email
     * @param password
     */
    function signin($email, $password) {
        $user = $this->userService->getUserByEmail(email);
    }

}


?>
