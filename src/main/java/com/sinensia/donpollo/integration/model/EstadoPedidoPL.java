package com.sinensia.donpollo.integration.model;

public enum EstadoPedidoPL {

	// TODO comprobar la lógica y el clean code...
	
    NUEVO, EN_PROCESO, PENDIENTE_ENTREGA, ENTREGADO, CANCELADO;

    public static boolean esTransicionValida(EstadoPedidoPL estadoActual, EstadoPedidoPL estadoNuevo) {

        switch (estadoActual) {
            case NUEVO:
                return estadoNuevo == EN_PROCESO || estadoNuevo == PENDIENTE_ENTREGA || estadoNuevo == ENTREGADO || estadoNuevo == CANCELADO;
            case EN_PROCESO:
                return estadoNuevo == PENDIENTE_ENTREGA || estadoNuevo == ENTREGADO || estadoNuevo == CANCELADO;
            case PENDIENTE_ENTREGA:
                return estadoNuevo == ENTREGADO || estadoNuevo == CANCELADO;
            case ENTREGADO:
                return estadoNuevo == EN_PROCESO;
            case CANCELADO:
                return false;
            default:
                return false;
        }

    }

}
