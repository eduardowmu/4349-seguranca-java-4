package br.com.forum_hub.infra.seguranca.totp;
import br.com.forum_hub.domain.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
public class TotpService {
    public String gerarSecret() {
        return null;
    }

    public String gerarQrCode(Usuario usuario) {
        // Otpauth URL: otpauth://totp/<Issuer>:<User>?secret=<Secret>&issuer=<Issuer>
        var issuer = "Fórum Hub";
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, usuario.getUsername(), usuario.getSecret(), issuer
        );
    }

    public Boolean verificarCodigo(String codigo, Usuario logado) {
        String secretDecoded = null;
        String codigoAplicacao = null;
        return codigoAplicacao.equals(codigo);
    }
}