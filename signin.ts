interface Session {
}

interface User {
    email: string,
    lastLoginAt: Date,
    session: Session,

    numLoginAttempts: number,

    /** return true if the passed in password is valid for this User */
    checkPassword: (p: string) => boolean,

    /** returns the updated user with a new session */
    generateSession: () => User,

    /** updates the lastLoginAt date and returns the updated User */
    updateLastLogin: () => User,

    /** sets the number of login attempts, returns the updated User */
    setNumLoginAttempts: (attempts: number) => User,

}

interface UserService {

    /** returns the User matching the email, otherwise returns null */
    getUserByEmail: (email: String) => User | null
}

namespace RouteImpls {

    const userService: UserService;

    /**
     * Logs a user in
     * 
     * Requirements:
     * - password must be correct
     * - can try up to 3 times
     * - updates lastloginAt date
     * - returns a new session
     * @param email 
     * @param password 
     */
    function signin(email: string, password: string): Session {
        let user = userService.getUserByEmail(email);
        user = user.setNumLoginAttempts(user.numLoginAttempts + 1);

        if (user.numLoginAttempts >= 3) {
            throw new Error("no more login attempts");
        }

        if (user.checkPassword(password)) {
            user.updateLastLogin();
            user.generateSession();
            return user.session;
        }

        throw new Error("invalid email or password");
    }
}