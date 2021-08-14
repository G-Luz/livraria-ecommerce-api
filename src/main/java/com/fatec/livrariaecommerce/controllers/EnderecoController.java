package com.fatec.livrariaecommerce.controllers;

import com.fatec.livrariaecommerce.dao.CidadeDao;
import com.fatec.livrariaecommerce.dao.TipoEnderecoDao;
import com.fatec.livrariaecommerce.facade.EnderecoFacade;
import com.fatec.livrariaecommerce.facade.GestaoClientesFacade;
import com.fatec.livrariaecommerce.models.domain.*;
import com.fatec.livrariaecommerce.models.domain.Endereco;
import com.fatec.livrariaecommerce.models.dto.EnderecoDTO;
import com.fatec.livrariaecommerce.models.utils.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/endereco")
public class EnderecoController {

    // ***********************************************************************

    private final EnderecoFacade enderecoFacade;
    private final CidadeDao cidadeDao;
    private final TipoEnderecoDao tipoEnderecoDao;
    private final GestaoClientesFacade gestaoClientesFacade;

    // ***********************************************************************

    @PostMapping(path = "{userId}")
    public ResponseEntity<Message> save(@PathVariable int userId, @RequestBody EnderecoDTO enderecoDto) {
        try {
            Cliente cliente = this.gestaoClientesFacade.findClienteByUsuarioId(userId).orElseThrow(Exception::new);
            Cidade cidade = this.cidadeDao.getOne(enderecoDto.getCidade().getId());
            TipoEndereco tipoEndereco = this.tipoEnderecoDao.getOne(enderecoDto.getTipoEndereco().getId());
            Endereco endereco = new Endereco(cliente);
            enderecoDto.fill(endereco, cidade, tipoEndereco);

            Resultado resultado = this.enderecoFacade.salvar(endereco);

            Message message = new Message();

            if (resultado.getMensagem() == null) {
                message.setTitle("Sucesso");
                message.setDescription("Endereco cadastrado com sucesso!");
                return ResponseEntity.ok(message);
            } else {
                message.setTitle("Erro");
                message.setDescription(resultado.getMensagem());
                return ResponseEntity.badRequest().body(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // ***********************************************************************

}
