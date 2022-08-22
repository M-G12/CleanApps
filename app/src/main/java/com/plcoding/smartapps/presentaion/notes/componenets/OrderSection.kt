package com.gharibe.smartapps.presentaion.notes.componenets

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gharibe.smartapps.feature_note.domain.util.NoteOrder
import com.gharibe.smartapps.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    orderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                checked = noteOrder is NoteOrder.Title,
                onCheck = { orderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                checked = noteOrder is NoteOrder.Date,
                onCheck = { orderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                checked = noteOrder is NoteOrder.Color,
                onCheck = { orderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                checked = noteOrder.orderType is OrderType.Ascending,
                onCheck = {
                    orderChange(noteOrder.copy(orderType = OrderType.Ascending))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                checked = noteOrder.orderType is OrderType.Descending,
                onCheck = {
                    orderChange(noteOrder.copy(orderType = OrderType.Descending))
                }
            )

        }
    }
}
