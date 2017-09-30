package pl.margoj.authenticator.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import pl.margoj.authenticator.entities.database.Account
import pl.margoj.authenticator.entities.database.Character
import pl.margoj.authenticator.entities.database.Server
import pl.margoj.authenticator.exceptions.InvalidParameterException
import pl.margoj.authenticator.exceptions.character.CharacterNameAlreadyTakenException
import pl.margoj.authenticator.exceptions.registration.ServerRegistrationClosedException
import pl.margoj.authenticator.repositories.AccountRepository
import pl.margoj.authenticator.repositories.CharacterRepository
import pl.margoj.authenticator.service.CharacterService
import pl.margoj.authenticator.service.ServerService
import java.util.Arrays
import java.util.Date

@Service
class CharacterServiceImpl @Autowired constructor
(
        val accountRepository: AccountRepository,
        val characterRepository: CharacterRepository,
        val serverService: ServerService
) : CharacterService
{
    private companion object
    {
        val VALID_PROFESSIONS = Arrays.asList('w', 'p', 'b', 'm', 't', 'h')

        val VALID_GENDERS = Arrays.asList('m', 'k')

        const val CHARACTER_REGEXP_ERROR = "Character name must contain only letters, numbers and '_' characters"
        const val CHARACTER_RANGE_ERROR = "Character name must contain of minimum __MIN and maximum __MAX charcters"
        const val CHARACTER_PROFESSION_ERROR = "Unknown profession"
        const val CHARACTER_GENDER_ERROR = "Unknown gender"
    }

    @Value("\${margoj.validate.character.range.min}")
    private var characterRangeMin: Int? = null

    @Value("\${margoj.validate.character.range.max}")
    private var characterRangeMax: Int? = null

    @Value("\${margoj.validate.character.regexp}")
    private lateinit var characterRegexpString: String

    @Value("\${margoj.validate.character.regexp.error}")
    private lateinit var characterErrorRegexp: String

    @Value("\${margoj.validate.character.range.error}")
    private lateinit var characterErrorRange: String

    @Value("\${margoj.validate.character.profession.error}")
    private lateinit var characterErrorProfession: String

    @Value("\${margoj.validate.character.gender.error}")
    private lateinit var characterErrorGender: String

    private val characterRegexp: Regex by lazy { this.characterRegexpString.toRegex() }

    override fun createCharacter(account: Account, server: Server, characterName: String, profession: Char, gender: Char): Character
    {
        if (!this.serverService.canEveryoneRegister(server))
        {
            throw ServerRegistrationClosedException(server.serverId!!, server.serverName!!)
        }

        if (characterName.length !in this.characterRangeMin!!..this.characterRangeMax!!)
        {
            throw InvalidParameterException(
                    "character_name",
                    CHARACTER_RANGE_ERROR.replace("__MIN", this.characterRangeMin!!.toString()).replace("__MAX", this.characterRangeMax!!.toString()),
                    this.characterErrorRange
            )
        }

        if (!characterName.matches(this.characterRegexp))
        {
            throw InvalidParameterException("character_name", CHARACTER_RANGE_ERROR, this.characterErrorRegexp)
        }

        if (!VALID_PROFESSIONS.contains(profession))
        {
            throw InvalidParameterException("profession", CHARACTER_PROFESSION_ERROR, this.characterErrorProfession)
        }

        if (!VALID_GENDERS.contains(gender))
        {
            throw InvalidParameterException("gender", CHARACTER_GENDER_ERROR, this.characterErrorGender)
        }

        val existingCharacter = this.characterRepository.findByServerAndNameIgnoreCase(server, characterName)
        if (existingCharacter != null)
        {
            throw CharacterNameAlreadyTakenException(existingCharacter.name!!)
        }

        val character = Character(
                name = characterName,
                profession = profession,
                gender = gender,
                level = 1,
                server = server,
                ownerAccount = account,
                creationDate = Date()
        )

        account.characters!!.add(character)
        this.accountRepository.save(account)

        return character
    }
}
