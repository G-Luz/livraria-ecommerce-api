package com.fatec.livrariaecommerce.models.dto;

import com.fatec.livrariaecommerce.models.domain.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ClienteDTO {
    private int id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNascimento;
    private String cpf;
    private String genero;
    private String email;
    private String senha;
    private String confirmacaoSenha;
    private List<TelefoneDTO> telefones;
    private List<EnderecoDTO> enderecos;

    public void fill(Cliente dominio) {
        Usuario usuario;
        if (dominio.getUsuario() == null) {
            usuario = new Usuario(this.getEmail(), this.getSenha(), PerfilUsuario.CLIENTE);
        } else {
            usuario = dominio.getUsuario();
        }

        dominio.atualizarDados(this.id, this.nome, this.sobrenome, this.dataNascimento, this.cpf,
                this.genero, usuario);
    }

    public static ClienteDTO from(Cliente cliente, Usuario usuario) {
        ClienteDTO dto = new ClienteDTO();
        dto.id = cliente.getId();
        dto.nome = cliente.getNome();
        dto.sobrenome = cliente.getSobrenome();
        dto.dataNascimento = cliente.getDataNascimento();
        dto.cpf = cliente.getCpf();
        dto.genero = cliente.getGenero();
        dto.email = usuario.getEmail();
        dto.senha = usuario.getSenha();
        dto.telefones = cliente.getTelefones().stream().map(TelefoneDTO::from).collect(Collectors.toList());
        dto.enderecos = cliente.getEnderecos().stream().map(EnderecoDTO::from).collect(Collectors.toList());;
        return dto;
    }

}
