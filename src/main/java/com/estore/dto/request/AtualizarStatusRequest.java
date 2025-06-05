package com.estore.dto.request;

import com.estore.model.StatusPedido;
import lombok.Data;

@Data
public class AtualizarStatusRequest {
    private StatusPedido status;
}