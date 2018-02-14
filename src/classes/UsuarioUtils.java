package classes;
//Controle de sessão adapatado conforme tutorial desse site: http://www.cauirs.com.br/rafael/?p=119
import java.util.HashMap;

public class UsuarioUtils {
	//<Codigo Usuario, login>
		public static HashMap<Integer, String> usuariosLogados = new HashMap<Integer, String>();
		
		public Boolean isUsuarioLogado(Integer codigoUsuario) {
			return usuariosLogados.containsKey(codigoUsuario);
		}
		
		public void retirarUsuarioLista(Integer codigoUsuario) {
			usuariosLogados.remove(codigoUsuario);
		}
}
