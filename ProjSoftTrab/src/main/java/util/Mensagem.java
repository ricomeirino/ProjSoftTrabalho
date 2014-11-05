package util;

import java.util.ArrayList;
import java.util.List;

public class Mensagem {

	private static final List<Integer> codigos = new ArrayList<Integer>();
	
	static {
		codigos.add(0); // Sucesso;
		codigos.add(1); // Registro duplicado
		codigos.add(2); // Falha na inclusão
		codigos.add(3); // Falha na exclusão
		codigos.add(4); // Falha na atualização
		codigos.add(5); // Registro não pode ser excluído da base
		codigos.add(6); // Registro não pode ser alterado 
		
	}
	
	
	public Mensagem(int codigo, String msg) {
		
		if (codigos.contains(codigo)){
			this.codigo = codigo;
			this.msg = msg;			
		}
		

	}
	private int codigo;
	private String msg;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
