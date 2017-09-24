package pl.margoj.authenticator.util

import pl.margoj.authenticator.exceptions.NullParameterException

class NonNullValidator
{
    private var parameters: MutableList<String>? = null

    fun check(any: Any?, name: String)
    {
        if(any == null)
        {
            if(parameters == null)
            {
                parameters = arrayListOf()
            }

            this.parameters!!.add(name)
        }
    }

    fun validate()
    {
        if(parameters != null && parameters!!.isNotEmpty())
        {
            throw NullParameterException(parameters!!)
        }
    }
}