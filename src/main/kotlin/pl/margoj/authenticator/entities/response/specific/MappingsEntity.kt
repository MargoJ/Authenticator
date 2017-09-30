package pl.margoj.authenticator.entities.response.specific

import pl.margoj.authenticator.entities.response.standard.OkResponse

data class MappingsEntity
(
        var availableRoutes: Collection<String>
) : OkResponse()