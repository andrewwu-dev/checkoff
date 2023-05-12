package com.andrew.checkoff

import com.andrew.checkoff.core.nav.Route
import com.andrew.checkoff.core.nav.UiEvent
import com.andrew.checkoff.feature.todo.TodoViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class TodoViewModelTest {
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var viewModel: TodoViewModel
    private lateinit var fakeTaskRepository: FakeTaskRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeTaskRepository = FakeTaskRepository()
        viewModel = TodoViewModel(
            taskRepository = fakeTaskRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `onAddTaskPressed should send navigate event for add edit screen`() = runTest {
        viewModel.onAddTaskPressed()
        assertEquals(viewModel.uiEvent.first(), UiEvent.Navigate(Route.ADD_EDIT_TASK))
    }

    @Test
    fun `onCheckBoxPressed should update task`() = runTest {
        viewModel.onCheckBoxPressed(fakeTaskRepository.tasks.first())
        delay(1000) // delay to wait for viewmodel to get updated
        assertEquals(fakeTaskRepository.tasks.first().completed, true)
    }

    @Test
    fun `onTaskPressed should send navigate event with task id`() = runTest {
        val task = fakeTaskRepository.tasks.first()
        viewModel.onTaskPressed(task)
        assertEquals(
            viewModel.uiEvent.first(),
            UiEvent.Navigate(Route.ADD_EDIT_TASK + "?taskId=${task.id}")
        )
    }

    @Test
    fun `onTaskSwiped should delete task`() = runTest {
        val task = fakeTaskRepository.tasks.first()
        viewModel.onTaskSwiped(task, UiEvent.ShowSnackbar("", ""))
        delay(1000)
        assertEquals(viewModel.viewState.value.tasks.count(), 1)
    }

    @Test
    fun `onUndoDeletePressed should add task back`() = runTest {
        val task = fakeTaskRepository.tasks.first()
        viewModel.onTaskSwiped(task, UiEvent.ShowSnackbar("", ""))
        delay(1000)
        assertEquals(viewModel.viewState.value.tasks.count(), 1)
        viewModel.onUndoDeletePressed()
        delay(1000)
        assertEquals(viewModel.viewState.value.tasks.count(), 2)
    }
}