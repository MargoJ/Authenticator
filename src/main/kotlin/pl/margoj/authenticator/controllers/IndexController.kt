package pl.margoj.authenticator.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.margoj.authenticator.entities.database.AccountRole
import pl.margoj.authenticator.entities.response.specific.MappingsEntity
import pl.margoj.authenticator.service.AuthenticationService
import java.util.Arrays

@RestController
class IndexController @Autowired constructor(val authenticationService: AuthenticationService)
{
    private companion object
    {
        val mappings = HashMap<AccountRole, MutableList<String>>()

        init
        {
            mappings.put(AccountRole.USER, ArrayList(Arrays.asList(
                    "GET /", "POST /register", "POST /authenticate", "GET /validate", "POST /renew", "POST /invalidate",
                    "GET /servers/list",
                    "GET /server/{serverId}", "POST /server/{serverId}/join", "POST /server/{serverId}/invalidateToken",
                    "POST /character/add", "GET /character/identifyGame"
            )))

            mappings.put(AccountRole.GAME_MASTER, ArrayList(Arrays.asList(
                    "GET /test/game-master"
            )))

            mappings.put(AccountRole.SUPER_GAME_MASTER, ArrayList(Arrays.asList(
                    "GET /test/super-game-master"
            )))

            mappings.put(AccountRole.ADMINISTRATOR, ArrayList(Arrays.asList(
                    "POST /servers/add",
                    "PATCH /server/{serverId}", "GET /server/{serverId}/secret"
            )))

            mappings.get(AccountRole.GAME_MASTER)!!.addAll(mappings.get(AccountRole.USER)!!)
            mappings.get(AccountRole.SUPER_GAME_MASTER)!!.addAll(mappings.get(AccountRole.GAME_MASTER)!!)
            mappings.get(AccountRole.ADMINISTRATOR)!!.addAll(mappings.get(AccountRole.SUPER_GAME_MASTER)!!)
        }
    }

    @RequestMapping("/")
    fun index(@RequestParam("account_token", required = false) token: String?): MappingsEntity
    {
        val role: AccountRole
        if (token == null)
        {
            role = AccountRole.USER
        }
        else
        {
            role = this.authenticationService.validate(token).accountRole!!
        }

        return MappingsEntity(mappings[role]!!)
    }
}