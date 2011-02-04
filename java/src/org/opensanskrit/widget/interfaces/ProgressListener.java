package org.opensanskrit.widget.interfaces;

import java.util.EventListener;
import org.opensanskrit.widget.ProgressEvent;

public interface ProgressListener extends EventListener {
    public void objReceived(ProgressEvent evt);
}