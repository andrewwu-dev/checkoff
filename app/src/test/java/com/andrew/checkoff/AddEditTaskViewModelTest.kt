package com.andrew.checkoff

import androidx.lifecycle.SavedStateHandle
import com.andrew.checkoff.core.model.TaskItem
import com.andrew.checkoff.core.nav.UiEvent
import com.andrew.checkoff.feature.add_edit_task.AddEditTaskViewModel
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

class AddEditTaskViewModelTest {
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var addTaskViewModel: AddEditTaskViewModel
    private lateinit var editTaskViewModel: AddEditTaskViewModel
    private lateinit var fakeTaskRepository: FakeTaskRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeTaskRepository = FakeTaskRepository()
        addTaskViewModel = AddEditTaskViewModel(
            taskRepository = fakeTaskRepository,
            savedStateHandle = SavedStateHandle()
        )
        editTaskViewModel = AddEditTaskViewModel(
            taskRepository = fakeTaskRepository,
            savedStateHandle = SavedStateHandle().apply {
                set("taskId", fakeTaskRepository.tasks.first().id)
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `onDonePressed should send UiEvent NavigateBack`() = runTest {
        addTaskViewModel.onDonePressed()
        assertEquals(addTaskViewModel.uiEvent.first(), UiEvent.PopBackStack)
    }

    @Test
    fun `onTitleChanged should update title`() = runTest {
        val title = "New Title"
        addTaskViewModel.onTitleChanged(title)
        assertEquals(addTaskViewModel.viewState.first().title, title)
    }

    @Test
    fun `onDescChanged should update desc`() = runTest {
        val desc = "New Desc"
        addTaskViewModel.onDescChanged(desc)
        assertEquals(addTaskViewModel.viewState.first().desc, desc)
    }

    @Test
    fun `onDonePressed new task should add task`() = runTest {
        val task = TaskItem(
            title = "New Task",
            desc = "New Task Description",
            completed = false
        )
        addTaskViewModel.onTitleChanged(task.title)
        addTaskViewModel.onDescChanged(task.desc)
        addTaskViewModel.onDonePressed()
        delay(1000)
        assertEquals(fakeTaskRepository.tasks.count(), 3)
    }

    @Test
    fun `onDonePressed existing task should update task`() = runTest {
        val task = TaskItem(
            title = "New Task",
            desc = "New Task Description",
            completed = false
        )
        editTaskViewModel.onTitleChanged(task.title)
        editTaskViewModel.onDescChanged(task.desc)
        editTaskViewModel.onDonePressed()
        delay(1000)
        assertEquals(fakeTaskRepository.tasks.count(), 2)
        assertEquals(fakeTaskRepository.tasks.first().title, task.title)
        assertEquals(fakeTaskRepository.tasks.first().desc, task.desc)
    }
}