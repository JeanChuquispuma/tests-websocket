package pe.farmaciasperuanas.izipaywebsocket.dto;

import lombok.Data;

@Data
public class OrderMessageDto {
	private String codFPago;
	private Double impPago;
	private String tipMoneda;
	private Double valVuelto;
	private Double impTotalPago;
	private String numAutPinpad;
}
