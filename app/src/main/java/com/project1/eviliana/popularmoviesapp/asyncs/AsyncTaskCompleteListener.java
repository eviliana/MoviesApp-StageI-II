package com.project1.eviliana.popularmoviesapp.asyncs;

/**
 * This is a useful callback mechanism so we can abstract our AsyncTasks out into separate, re-usable
 * and testable classes yet still retain a hook back into the calling activity. Basically, it'll make classes
 * cleaner and easier to unit test. This is a solution from James Elsey blog for extacting Async Tasks
 *
 * @param <T>
 */
public interface AsyncTaskCompleteListener<T>
{
    /**
     * Invoked when the AsyncTask has completed its execution.
     * @param result The resulting object from the AsyncTask.
     */
    public void onTaskComplete(T result);
}