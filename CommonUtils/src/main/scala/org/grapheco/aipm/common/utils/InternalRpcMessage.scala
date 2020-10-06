package org.grapheco.aipm.common.utils


trait InternalRpcRequest {

}

trait InternalRpcResponse {

}

case class AuthenticationRequest() extends InternalRpcRequest {

}

case class AuthenticationResponse() extends InternalRpcResponse {

}