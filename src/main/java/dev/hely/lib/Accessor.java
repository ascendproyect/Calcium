package dev.hely.lib;

import java.util.Collection;

/**
 * @author Maykol Morales Morante (zSirSpectro)
 * Sunday, July 18, 2021
 */

public interface Accessor<H> {

    H get();

    void set(H value);

    void add(Collection<H> collection);

}
