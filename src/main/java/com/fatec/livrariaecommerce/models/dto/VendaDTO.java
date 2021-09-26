package com.fatec.livrariaecommerce.models.dto;

import com.fatec.livrariaecommerce.models.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class VendaDTO {

    private int id;

    @JoinColumn(name = "id_endereco")
    private int idEndereco;
    private int idCliente;
    private double valorTotal;
    private List<ItensPedidoDTO> itensPedido;
    private List<FormaPagamentoDTO> formasPagamento;
    private List<CupomDTO> cupoms;

    public void fill(Venda dominio) {
        List<ItensPedido> itensPedidos = new ArrayList<>();
        List<FormaPagamento> formaPagamentoList = new ArrayList<>();
        List<Cupom> cupomList = new ArrayList<>();

        if (!this.getItensPedido().isEmpty()) {
            for (ItensPedidoDTO itensPedidoDTO : this.getItensPedido()) {
                ItensPedido itensPedido = new ItensPedido(dominio);
                itensPedidoDTO.fill(itensPedido);
                itensPedidos.add(itensPedido);
            }
        }

        if (!this.getFormasPagamento().isEmpty()) {
            for (FormaPagamentoDTO formaPagamentoDTOs : this.getFormasPagamento()) {
                FormaPagamento formaPagamento = new FormaPagamento(dominio);
                formaPagamentoDTOs.fill(formaPagamento);
                formaPagamentoList.add(formaPagamento);
            }
        }

        if (!this.getCupoms().isEmpty()) {
            for (CupomDTO cupomDTO : this.getCupoms()) {
                Cupom cupom = new Cupom();
                cupomDTO.fill(cupom);
                cupomList.add(cupom);
            }
        }
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);

        dominio.atualizarDados(this.id, this.idEndereco, cliente, this.valorTotal, itensPedidos,
                formaPagamentoList, cupomList);
    }

    public static VendaDTO from(Venda venda) {
        VendaDTO dto = new VendaDTO();
        dto.id = venda.getId();
        dto.idEndereco = venda.getIdEndereco();
        dto.idCliente = venda.getCliente().getId();
        dto.valorTotal = venda.getValorTotal();
        dto.itensPedido = venda.getItensPedidos().stream().map(ItensPedidoDTO::from).collect(Collectors.toList());
        dto.formasPagamento = venda.getFormaPagamentoList().stream().map(FormaPagamentoDTO::from).collect(Collectors.toList());
        dto.cupoms = venda.getCupoms().stream().map(CupomDTO::from).collect(Collectors.toList());
        return dto;
    }
}
