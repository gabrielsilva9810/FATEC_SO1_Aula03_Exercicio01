package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {

	public RedesController() {
		super();
	}

	// -----------------------------------------ENUNCIADO------------------------------------------------------//
	// 1) NOME DO SISTEMA
	// 2) CHAMA IP
	// 3) CHAMA PING

// 1)  EXIBIR O NOME DO SISTEMA OPERACIONAL
	private String os() {
		String so = System.getProperty("os.name");
		String arq = System.getProperty("os.arch");
		String version = System.getProperty("os.version");

		return so + "Nome do Sistema Operacional: " + so + "\nArquitetura: " + arq + "\nVersão: " + version;
	}

//-------------------------------------------------------------------------------------------------------------//
// 2) CHAMA IP - DE ACORDO COM SISTEMA OPERACIONAL
	public void ip() {

		// VALIDACAO SISTEMA OPERACIONAL
		String os = os(); // MENCIONANDO O METODO ACIMA
		String chamada; // O NOME DO COMANDO DADO

		// UTILIZAÇÃO DOS COMANDOS - EXIBIR PING
		if (os.contains("Win")) {
			chamada = "IPCONFIG"; // WINDOWS
		} else {
			chamada = "IFCONFIG"; // LINUX
		}

		// TRY COM A LEITURA DO PROCESSO
		try {
			Process p = Runtime.getRuntime().exec(chamada); // ENQUANTO TIVER EXECUCAO, FICA SALVO

			InputStream fluxo = p.getInputStream(); // FLUXO DE ENTRADA DE DADOS
			InputStreamReader leitor = new InputStreamReader(fluxo); // ELE LÊ E TROCA PARA STRING

			BufferedReader buffer = new BufferedReader(leitor); // CONVERTENDO, SALVA NO BUFFER

			// LENDO A PRIMEIRA LINHA DO BUFFER
			String linha = buffer.readLine(); // APÓS ISSO, DESCARTA

			// ENQUANTO FOR DIFERENTE DE NULL | --> ATÉ SE ESGOTAR
			while (linha != null) {

				// VERIFICAR NO WINDOWS (COMANDO IPCONFIG -> IPV4)
				if (linha.contains("IPv4")) {
					System.out.println(linha); // PRINTA O CONTEUDO DA LINHA
				}
				linha = buffer.readLine(); // LE A LINHA NOVAMENTE

			}

			// SE FOR NO LINUX, VAI PUXAR ESSE IF:
			if (chamada == "IFCONFIG") {

				// VERIFICAR NO LINUX (COMANDO IFCONFIG -> INET)
				if (linha.contains("inet")) {
					System.out.println(linha); // PRINTA O CONTEUDO DA LINHA
				}
				linha = buffer.readLine(); // LE A LINHA NOVAMENTE
			}

			// TERMINOU? -> FECHAR TUDO
			buffer.close();
			leitor.close();
			fluxo.close();

			// TRATAR O ERRO E EXIBE CODIGO DE ERRO
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//-------------------------------------------------------------------------------------------------------------//	
// 3) CHAMA PING - DE ACORDO COM SISTEMA OPERACIONAL
	public void ping() {

		// VALIDACAO SISTEMA OPERACIONAL
		String os = os(); // MENCIONANDO O METODO ACIMA
		String chamada; // O NOME DO COMANDO DADO

		// UTILIZAÇÃO DOS COMANDOS - PINGAR
		if (os.contains("Win")) {
			chamada = "PING -4 -n 10 www.google.com.br"; // WINDOWS
		} else {
			chamada = "PING -4 -c 10 www.google.com.br"; // LINUX
		}

		try {

			Process p = Runtime.getRuntime().exec(chamada); // ENQUANTO TIVER EXECUCAO, FICA SALVO

			InputStream fluxo = p.getInputStream(); // FLUXO DE ENTRADA DE DADOS
			InputStreamReader leitor = new InputStreamReader(fluxo); // ELE LÊ E TROCA PARA STRING

			BufferedReader buffer = new BufferedReader(leitor); // CONVERTENDO, SALVA NO BUFFER

			// LENDO A PRIMEIRA LINHA DO BUFFER
			String linha = buffer.readLine(); // APÓS ISSO, DESCARTA

			// ACALMAR O USUÁRIO 
			System.out.println("Ping está sendo realizado...");
			System.out.println("Aguardando média...");

			// VERIFICAR LINHAS DE SAIDA
			while (linha != null) {
				if (linha.contains("dia =")) { // EXIBE O VALOR DA PARTE DEBAIXO -> MÉDIA
					String[] partes = linha.split(" ");

					// TEMPO MEDIO DO PING
					for (String parts : partes) {
						if (!parts.contains("ms,") && parts.contains("ms")) {
							System.out.println("\nMedia: " + parts);

						}
					}
				}
				linha = buffer.readLine();
			}
			
			// SE FOR NO LINUX, VAI PUXAR ESSE IF:
			if (chamada == "PING -4 -c 10 www.google.com.br") {

				// VERIFICAR LINHAS DE SAIDA
				while (linha != null) {
					if (linha.contains("dia =")) { // EXIBE O VALOR DA PARTE DEBAIXO -> MÉDIA
						String[] partes = linha.split(" ");

						// TEMPO MEDIO DO PING
						for (String parts : partes) {
							if (!parts.contains("ms,") && parts.contains("ms")) {
								System.out.println("\nMedia: " + parts);

							}
							linha = buffer.readLine();
						}

						// TERMINOU? -> FECHAR TUDO
						buffer.close();
						leitor.close();
						fluxo.close();

					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
