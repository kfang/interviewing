
/**
  * WARNING: this code review adapted from typescript.
  *   functions can return null, and there are no Options.
  */

trait Session {
  // this is just a stub, don't worry, its an actual session.
}

trait User {

  var email: String
  var lastLoginAt: Long
  var session: Session
  var numLoginAttempts: Int

  /** return true if the passed in password is valid for this User */
  def checkPassword(p: String): Boolean

  /** returns the updated user with a new session */
  def generateSession(): User

  /** updates the lastLoginAt date and returns the updated User */
  def updateLastLogin(): User

  /** sets the number of login attempts, returns the updated User */
  def setNumLoginAttempts(attempts: Int): User

}

trait UserService {

  /** returns the User matching the email, otherwise returns null */
  def getUserByEmail(email: String): User

}

class RouteImpls {

  val userService: UserService

  /**
    * POST "/auth/signin"
    *
    * Logs a user in
    * Requirements:
    * - password must be correct
    * - can try up to 3 times
    * - updates lastloginAt date
    * - returns a new session
    */
  def signin(email: String, password: String): Session = {
    var user = userService.getUserByEmail(email)
    user = user.setNumLoginAttempts(user.numLoginAttempts + 1)

    if (user.numLoginAttempts >= 3) {
      throw new Unauthorized(401, "no more login attempts")
    }

    if (user.checkPassword(password)) {
      user.updateLastLogin()
      user.generateSession()
      return user.session
    }

    throw new BadRequest(400, "invalid email or password")
  }

}
