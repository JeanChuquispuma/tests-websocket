package pe.farmaciasperuanas.izipaywebsocket.services;

public interface IAbaxBackendJwtService {
	public boolean validateAuthHeaderJwt(String bearerToken);
}
