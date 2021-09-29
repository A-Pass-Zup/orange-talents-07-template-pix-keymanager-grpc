package br.com.zupacademy.apass.pix.keymanagergrpc

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.zupacademy.apass.pixkeymanager")
		.start()
}

