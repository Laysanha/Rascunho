import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DadosConexao {

	static String driver="org.postgresql.Driver";
	static String user="postgres";
	static String senha="boneca123";
	static String url="jdbc:postgresql://localhost:5432/postgres";
	static String sqlSelect="SELECT * FROM pessoas order by id";
	static String sqlInsert="insert into pessoas "
			+ "(id, nome, endereco, cidade, profissao) "
			+ "values (?, ?, ?, ?, ?)";
	
	static void listar() {
		try {
			Class.forName(driver);//informo driver             

			Connection con = (Connection) DriverManager.getConnection
					(url, user, senha);//conexao
			
			PreparedStatement stmt = con.prepareStatement(sqlSelect);//preparar
			
			ResultSet rs = stmt.executeQuery();//executar
			
			while(rs.next()){
				System.out.println("Pessoa:  "+ 
							rs.getInt("id")+
							" - "+ 
							rs.getString("nome"));
				//System.out.println("Cidade   : "+ rs.getString("cidade"));
				//System.out.println("Profissao: "+ rs.getString("profissao"));
				//System.out.println("Endereco : "+ rs.getString("endereco"));
			}
			rs.close();
			con.close();
			
		} catch (ClassNotFoundException ex) {
			System.err.print(ex.getMessage());
			
		} catch (SQLException e) {
			
			System.err.print(e.getMessage());
		}
	}	
	
	static void incluir(Pessoa p1) {
		try {
			Class.forName(driver);
			Connection con = (Connection) DriverManager.getConnection(url, user, senha);
			PreparedStatement stmt = con.prepareStatement(sqlInsert);
			
			stmt.setInt(1, p1.getId());
			stmt.setString(2, p1.getNome());
			stmt.setString(3, p1.getEndereco());
			stmt.setString(4, p1.getCidade());
			stmt.setString(5, p1.getProfissao());
			
			stmt.execute();
			
		} catch (ClassNotFoundException ex) {
			System.err.print(ex.getMessage());
		} catch (SQLException e) {
			System.err.print(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Integer opcao=0;
		
		Integer id;
		String nome;
		String cidade;
		String profissao;
		String endereco;
		
		String mensagem="1- Inserir\n"+
						"2- Listar\n"+
						"99- Sair";	
		
		while (opcao !=99) {
			opcao=Integer.parseInt(JOptionPane.showInputDialog(mensagem));
			
			switch (opcao) {
				case 1:{
					id=Integer.parseInt(JOptionPane.showInputDialog("ID"));
					nome=JOptionPane.showInputDialog("Nome");
					cidade=JOptionPane.showInputDialog("Cidade");
					profissao=JOptionPane.showInputDialog("Profissao");
					endereco=JOptionPane.showInputDialog("Endere�o");
					
					Pessoa p=new Pessoa();
					
					p.setId(id);
					p.setNome(nome);
					p.setCidade(cidade);
					p.setEndereco(endereco);
					p.setProfissao(profissao);
					
					incluir(p);
					break;
				}
				case 2:{
					listar();
					break;
				}
				case 99:{
					//
					break;
				}
				default:{
					JOptionPane.showMessageDialog(null, "Op��o Inv�lida");
					break;
				}
			}
		}		
	}
}