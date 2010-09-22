/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.siscomercio.managers;

import com.siscomercio.utilities.SystemUtil;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * <p>This class is made to handle all the ThreadPools.</p>
 *
 * everything else that needs to be scheduled.<br>
 * @author -Wooden-
 *
 */
public class ThreadPoolManager
{
    /**
     *
     */
    protected static final Logger _log = Logger.getLogger(ThreadPoolManager.class.getName());
    private ScheduledThreadPoolExecutor _generalScheduledThreadPool;
    private ScheduledThreadPoolExecutor _aiScheduledThreadPool;
    private ThreadPoolExecutor _generalPacketsThreadPool;
    private ThreadPoolExecutor _ioPacketsThreadPool;
    private ThreadPoolExecutor _generalThreadPool;
    /** temp workaround for VM issue */
    private static final long MAX_DELAY = Long.MAX_VALUE / 1000000 / 2;
    private boolean _shutdown;

    /**
     * apenas uma instancia dessa classe
     * @return SingletonHolder._instance
     */
    public static ThreadPoolManager getInstance()
    {
        return SingletonHolder._instance;
    }

    private ThreadPoolManager()
    {
    }

    /**
     *
     * @param delay
     * @return delay
     */
    public static long validateDelay(long delay)
    {
        if(delay < 0)
            delay = 0;
        else
            if(delay > MAX_DELAY)
                delay = MAX_DELAY;
        return delay;
    }

    /**
     *
     * @param r
     * @param delay
     * @return  _generalScheduledThreadPool
     */
    public ScheduledFuture<?> scheduleGeneral(Runnable r, long delay)
    {
        try
        {
            delay = ThreadPoolManager.validateDelay(delay);
            return _generalScheduledThreadPool.schedule(r, delay, TimeUnit.MILLISECONDS);
        }
        catch(RejectedExecutionException e)
        {
            return null; /* shutdown, ignore */
        }
    }

    /**
     *
     * @param r
     * @param initial
     * @param delay
     * @return _generalScheduledThreadPool
     */
    public ScheduledFuture<?> scheduleGeneralAtFixedRate(Runnable r, long initial, long delay)
    {
        try
        {
            delay = ThreadPoolManager.validateDelay(delay);
            initial = ThreadPoolManager.validateDelay(initial);
            return _generalScheduledThreadPool.scheduleAtFixedRate(r, initial, delay, TimeUnit.MILLISECONDS);
        }
        catch(RejectedExecutionException e)
        {
            return null; /* shutdown, ignore */
        }
    }

    /**
     *
     * @param r
     * @return _generalScheduledThreadPool
     */
    public boolean removeGeneral(Runnable r)
    {
        return _generalScheduledThreadPool.remove(r);
    }

    /**
     *
     * @param r
     */
    public void executeTask(Runnable r)
    {
        _generalThreadPool.execute(r);
    }

    /**
     * retorna com relatorio dessa Classe
     * @return uma String com o Relatrio
     */
    public String[] getStats()
    {
        return new String[]
                {
                    "STP:",
                    " + Effects:",
                    " | -------",
                    " + General:",
                    " |- ActiveThreads:   " + _generalScheduledThreadPool.getActiveCount(),
                    " |- getCorePoolSize: " + _generalScheduledThreadPool.getCorePoolSize(),
                    " |- PoolSize:        " + _generalScheduledThreadPool.getPoolSize(),
                    " |- MaximumPoolSize: " + _generalScheduledThreadPool.getMaximumPoolSize(),
                    " |- CompletedTasks:  " + _generalScheduledThreadPool.getCompletedTaskCount(),
                    " |- ScheduledTasks:  " + (_generalScheduledThreadPool.getTaskCount() - _generalScheduledThreadPool.getCompletedTaskCount()),
                    " | -------",
                    " + AI:",
                    " |- ActiveThreads:   " + _aiScheduledThreadPool.getActiveCount(),
                    " |- getCorePoolSize: " + _aiScheduledThreadPool.getCorePoolSize(),
                    " |- PoolSize:        " + _aiScheduledThreadPool.getPoolSize(),
                    " |- MaximumPoolSize: " + _aiScheduledThreadPool.getMaximumPoolSize(),
                    " |- CompletedTasks:  " + _aiScheduledThreadPool.getCompletedTaskCount(),
                    " |- ScheduledTasks:  " + (_aiScheduledThreadPool.getTaskCount() - _aiScheduledThreadPool.getCompletedTaskCount()),
                    "TP:",
                    " + Packets:",
                    " |- ActiveThreads:   " + _generalPacketsThreadPool.getActiveCount(),
                    " |- getCorePoolSize: " + _generalPacketsThreadPool.getCorePoolSize(),
                    " |- MaximumPoolSize: " + _generalPacketsThreadPool.getMaximumPoolSize(),
                    " |- LargestPoolSize: " + _generalPacketsThreadPool.getLargestPoolSize(),
                    " |- PoolSize:        " + _generalPacketsThreadPool.getPoolSize(),
                    " |- CompletedTasks:  " + _generalPacketsThreadPool.getCompletedTaskCount(),
                    " |- QueuedTasks:     " + _generalPacketsThreadPool.getQueue().size(),
                    " | -------",
                    " + I/O Packets:",
                    " |- ActiveThreads:   " + _ioPacketsThreadPool.getActiveCount(),
                    " |- getCorePoolSize: " + _ioPacketsThreadPool.getCorePoolSize(),
                    " |- MaximumPoolSize: " + _ioPacketsThreadPool.getMaximumPoolSize(),
                    " |- LargestPoolSize: " + _ioPacketsThreadPool.getLargestPoolSize(),
                    " |- PoolSize:        " + _ioPacketsThreadPool.getPoolSize(),
                    " |- CompletedTasks:  " + _ioPacketsThreadPool.getCompletedTaskCount(),
                    " |- QueuedTasks:     " + _ioPacketsThreadPool.getQueue().size(),
                    " | -------",
                    " + General Tasks:",
                    " |- ActiveThreads:   " + _generalThreadPool.getActiveCount(),
                    " |- getCorePoolSize: " + _generalThreadPool.getCorePoolSize(),
                    " |- MaximumPoolSize: " + _generalThreadPool.getMaximumPoolSize(),
                    " |- LargestPoolSize: " + _generalThreadPool.getLargestPoolSize(),
                    " |- PoolSize:        " + _generalThreadPool.getPoolSize(),
                    " |- CompletedTasks:  " + _generalThreadPool.getCompletedTaskCount(),
                    " |- QueuedTasks:     " + _generalThreadPool.getQueue().size(),
                    " | -------"
                };
    }

    private class PriorityThreadFactory implements ThreadFactory
    {
        private int _prio;
        private String _name;
        private AtomicInteger _threadNumber = new AtomicInteger(1);
        private ThreadGroup _group;

        public PriorityThreadFactory(String name, int prio)
        {
            _prio = prio;
            _name = name;
            _group = new ThreadGroup(_name);
        }

        /* (non-Javadoc)
         * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
         */
        @Override
        public Thread newThread(Runnable r)
        {
            Thread t = new Thread(_group, r);
            t.setName(_name + "-" + _threadNumber.getAndIncrement());
            t.setPriority(_prio);
            return t;
        }

        public ThreadGroup getGroup()
        {
            return _group;
        }

    }

    /**
     *
     */
    public void shutdown()
    {
        _shutdown = true;
        try
        {

            _generalScheduledThreadPool.awaitTermination(1, TimeUnit.SECONDS);
            _generalPacketsThreadPool.awaitTermination(1, TimeUnit.SECONDS);
            _ioPacketsThreadPool.awaitTermination(1, TimeUnit.SECONDS);
            _generalThreadPool.awaitTermination(1, TimeUnit.SECONDS);

            _generalScheduledThreadPool.shutdown();
            _generalPacketsThreadPool.shutdown();
            _ioPacketsThreadPool.shutdown();
            _generalThreadPool.shutdown();
            _log.info("All ThreadPools are now stopped");

        }
        catch(InterruptedException e)
        {
            SystemUtil.showErrorMsg(e.getMessage(),true);
        }
    }

    /**
     *
     * @return _shutdown
     */
    public boolean isShutdown()
    {
        return _shutdown;
    }

    /**
     *
     */
    public void purge()
    {
        _generalScheduledThreadPool.purge();
        _aiScheduledThreadPool.purge();
        _ioPacketsThreadPool.purge();
        _generalPacketsThreadPool.purge();
        _generalThreadPool.purge();
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder
    {
        protected static final ThreadPoolManager _instance = new ThreadPoolManager();
    }

}
