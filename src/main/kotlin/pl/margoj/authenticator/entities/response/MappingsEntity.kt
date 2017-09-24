package pl.margoj.authenticator.entities.response

data class MappingsEntity
(
        var availableRoutes: Collection<String>
) : OkResponse()