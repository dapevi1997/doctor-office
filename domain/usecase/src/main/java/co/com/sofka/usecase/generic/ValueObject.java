package co.com.sofka.usecase.generic;

import java.io.Serializable;

public interface ValueObject<T> extends Serializable {
    T value();
}
