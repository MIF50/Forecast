package com.mif50.forecast.ui.base

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.mif50.forecast.utilies.extension.getLayoutRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    lateinit var baseActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        baseActivity = context as BaseActivity

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(getLayoutRes().menu != 0)
        job = Job()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes().layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (getLayoutRes().menu != 0) {
            inflater.inflate(getLayoutRes().menu, menu)
            onMenuCreated(menu)
        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onMenuItemClickListener(item, item.itemId)
        return super.onOptionsItemSelected(item)

    }


    abstract fun bindView(savedInstanceState: Bundle?)


    open fun onMenuCreated(menu: Menu?) {

    }

    open fun onMenuItemClickListener(item: MenuItem?, id: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}