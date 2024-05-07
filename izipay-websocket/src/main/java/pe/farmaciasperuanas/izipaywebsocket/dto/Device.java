package pe.farmaciasperuanas.izipaywebsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private String serial;
    private String deviceModel;
    private String deviceManufacturer;
}
