package Model;
public class Usuario {
	private String id;
	private String senha;
	private String nome;
	private String endere�o;
	private String telefone;
	private String email;
	
	public Usuario(String id,String senha,String nome,String end,String tel,String email){
		super();
		this.setId(id);
		this.setSenha(senha);
		this.setNome(nome);
		this.setEndere�o(end);
		this.setTelefone(tel);
		this.setEmail(email);
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndere�o() {
		return endere�o;
	}

	public void setEndere�o(String endere�o) {
		this.endere�o = endere�o;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
