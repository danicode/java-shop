package src.main.domain.resultado;

import java.util.ArrayList;
import java.util.List;

public class ResultadoValidacion<T> {

    private T objeto;
    private List<String> errores;

    public ResultadoValidacion(T objeto) {
        this.objeto = objeto;
        this.errores = new ArrayList<>();
    }

    public T getObjeto() {
        return objeto;
    }

    public List<String> getErrores() {
        return errores;
    }

    public boolean isValido() {
        return errores.isEmpty();
    }
}
