package ExternalProperties;

import javafx.beans.property.SimpleObjectProperty;

/**
 * This Class serves the purpose to be able to wrap simple fields with property functionality.
 * For example can there be a name, which is a String, but should not be a StringProperty for some reason.
 * With this Class the field can be managed externally like a property while still being just a String.
 * Problems come as soon as the managed field gets set outside this wrapper, because the wrapper cannot know there was
 * an update.
 * @param <T>
 */
public abstract class ExternalObjectProperty<T> extends SimpleObjectProperty<T> {

    /**
     * The constructor of {@code ExternalObjectProperty}
     *
     * @param initialValue
     *            the initial value of the wrapped value
     */
    public ExternalObjectProperty(T initialValue) {
        set(initialValue);
    }

    public ExternalObjectProperty(){};

    /**
     * defines the setter method for the field to be managed by this property.
     * every implementation must implement this method.
     * @param newValue the new value the managed field gets set to
     */
    protected abstract void setExternalValue(T newValue);

    /**
     * defines the getter method for the field to be managed by this property.
     * every implementation must implement this method.
     * @return the current value of the managed field.
     */
    protected abstract T getExternalValue();

    @Override
    public void set(T newValue){
        setExternalValue(newValue);
        fireValueChangedEvent();
    }

    @Override
    public T get(){
        return getExternalValue();
    }

    @Override
    public void setValue(T v) {
        set(v);
    }

    @Override
    public T getValue() {
        return get();
    }
}
