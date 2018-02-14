package teste;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.JOptionPane;
import classes.LocalInstalacao;
import classes.servico.ServicoLocalInstalacao;



public class Arquivo {

	String tag;
	String descricao;
	String idEquipe;
	boolean retorno;
	ServicoLocalInstalacao local = new ServicoLocalInstalacao();
	LocalInstalacao localInstalacao = new LocalInstalacao();
	@SuppressWarnings("resource")
	public void processa(){
		try {
			Scanner scanner = new Scanner(new FileReader("/home/josimar/Área de Trabalho/Leonardo Rodrigues de Souza.txt")).useDelimiter("\\||\\n");
			while(scanner.hasNext()){
				scanner.next();
				//JOptionPane.showMessageDialog(null, tag);
				//if(tag == "taglocal"){
					tag = scanner.next();
				//	JOptionPane.showMessageDialog(null, tag);
				//}
				scanner.next();
				//JOptionPane.showMessageDialog(null, descricao);
				//if(descricao == "descricao"){
					descricao = scanner.next();
					//JOptionPane.showMessageDialog(null, descricao);
				//}
				scanner.next();
				//JOptionPane.showMessageDialog(null, idEquipe);
				//if(idEquipe == "idEquipe"){
					idEquipe = scanner.next();
					//JOptionPane.showMessageDialog(null, idEquipe);
				//}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		localInstalacao.setTagLocal(tag);
		localInstalacao.setDescricao(descricao);
		localInstalacao.setIdEquipe(Integer.parseInt(idEquipe));
		localInstalacao.setIdFuncionario(2);
		retorno = local.insereLocalInstalacao(localInstalacao);
		if(retorno == true)
			JOptionPane.showMessageDialog(null, "Arquivo processado com sucesso!");
		else
			JOptionPane.showMessageDialog(null, "Arquivo processado com erro!");
	}
}

