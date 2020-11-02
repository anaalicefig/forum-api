package br.com.alura.forum.config.validacao;

public class FormErrorDTO {
  private String campo;
  private String messageError;

  public FormErrorDTO(String campo, String messageError) {
    this.campo = campo;
    this.messageError = messageError;
  }

  public String getCampo() {
    return campo;
  }

  public String getMessageError() {
    return messageError;
  }
}
