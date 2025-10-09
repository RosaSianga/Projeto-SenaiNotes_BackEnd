package br.com.senai.senainotes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ArmazenamentoService {

    // Guarda o caminho completo para a nossa pasta de uploads (ex: C:/projeto/uploads)
    private final Path localDeArmazenamento;

    /**
     * O construtor é chamado quando o Spring cria esta classe.
     * @Value("${file.upload-dir}"): Pega o valor da propriedade que definimos no application.properties
     * e o injeta na variável 'uploadDir'.
     */
    public ArmazenamentoService(@Value("${file.upload-dir}") String uploadDir) {
        this.localDeArmazenamento = Paths.get(uploadDir);
        try {
            // Garante que o diretório de uploads exista. Se não, ele o cria.
            Files.createDirectories(localDeArmazenamento);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar a pasta de uploads.", e);
        }
    }

    /**
     * Salva um arquivo no disco.
     * @param arquivo O arquivo que veio da requisição.
     * @return O nome único gerado para o arquivo.
     */
    public String salvarArquivo(MultipartFile arquivo) {
        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));

            // Gera um nome aleatório e único para o arquivo para evitar conflitos.
            String nomeUnico = UUID.randomUUID().toString() + extensao;

            Path caminhoDestino = localDeArmazenamento.resolve(nomeUnico);

            // Copia os bytes do arquivo para o destino no servidor.
            try (InputStream inputStream = arquivo.getInputStream()) {
                Files.copy(inputStream, caminhoDestino, StandardCopyOption.REPLACE_EXISTING);
            }
            return nomeUnico; // Retorna o novo nome para ser salvo no banco.
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o arquivo.", e);
        }
    }

    /**
     * Carrega um arquivo do disco.
     * @param nomeDoArquivo O nome do arquivo que queremos buscar.
     * @return Um objeto Resource que representa o arquivo para ser enviado na resposta.
     */
    public Resource carregarArquivo(String nomeDoArquivo) {
        try {
            Path arquivo = localDeArmazenamento.resolve(nomeDoArquivo);
            Resource resource = new UrlResource(arquivo.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Não foi possível ler o arquivo: " + nomeDoArquivo);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao tentar carregar o arquivo: " + nomeDoArquivo, e);
        }
    }
}