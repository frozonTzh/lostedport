package com.company.lostedport

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.util.*


class MutableHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val headers: MutableMap<String, MutableSet<String>> = HashMap(10)

    init {
        val headerNames = super.getHeaderNames()
        while (headerNames.hasMoreElements()) {
            val name = headerNames.nextElement()
            val h = super.getHeaders(name)
            val headersSet: MutableSet<String> = HashSet(2)
            headers[name.lowercase()] = headersSet
            while (h.hasMoreElements()) {
                headersSet.add(h.nextElement())
            }
        }
    }


    override fun getHeader(name: String?): String? {
        return headers[name?.lowercase()]?.stream()?.findAny()?.orElse(null)
    }

    override fun getHeaders(name: String?): Enumeration<String> {
        return Collections.enumeration(headers[name?.lowercase()] ?: HashSet(0))
    }

    override fun getHeaderNames(): Enumeration<String> {
        return Collections.enumeration(headers.keys)
    }

    fun putHeader(name: String, value: String) {
        val nameLow = name.lowercase()
        if (!headers.containsKey(nameLow)) {
            headers[nameLow] = HashSet()
        }
        headers[nameLow]!!.add(value)
    }

    fun rmHeaders(nameLow: String) {
        headers.remove(nameLow)
    }

    fun getHeaderMap(): MutableMap<String, MutableSet<String>> {
        return headers
    }
}
