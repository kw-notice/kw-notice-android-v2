package dev.yjyoon.kwnotice.presentation.ui.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.yjyoon.kwnotice.domain.model.Notice
import dev.yjyoon.kwnotice.presentation.R
import dev.yjyoon.kwnotice.presentation.ui.component.KwNoticeBadge
import dev.yjyoon.kwnotice.presentation.ui.component.KwNoticeRoundRect
import dev.yjyoon.kwnotice.presentation.ui.theme.KwNoticeTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NoticeCard(
    notice: Notice,
    onClickNotice: (String) -> Unit,
    bookmarked: Boolean,
    onToggleBookmark: (Notice, Boolean) -> Unit
) {
    ElevatedCard(
        onClick = { onClickNotice(notice.url) },
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            KwNoticeRoundRect(width = 4.dp, radius = 0.dp)
            Column(
                Modifier
                    .padding(start = 12.dp, top = 12.dp, end = 8.dp, bottom = 10.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                NoticeTitle(notice)
                Spacer(Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NoticeDateBadge(notice)
                    if (notice is Notice.KwHome) NoticeTagBadge(notice)
                    if (notice is Notice.KwHome) NoticeDepartmentBadge(notice)
                }
            }
            FilledIconToggleButton(
                checked = bookmarked,
                onCheckedChange = { onToggleBookmark(notice, it) },
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Icon(
                    painter = if (bookmarked) {
                        painterResource(id = R.drawable.ic_star_filled)
                    } else {
                        painterResource(
                            id = R.drawable.ic_star_outlined
                        )
                    },
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun NoticeTitle(notice: Notice) {
    val titleString = when (notice) {
        is Notice.KwHome -> {
            notice.title.substring(notice.tag.length + 3)
        }
        is Notice.SwCentral -> {
            notice.title
        }
    }

    Text(
        text = titleString,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
fun NoticeDateBadge(notice: Notice) {
    val date = when (notice) {
        is Notice.KwHome -> {
            notice.modifiedDate
        }
        is Notice.SwCentral -> {
            notice.postedDate
        }
    }

    KwNoticeBadge(
        leadingIconRes = R.drawable.ic_calendar,
        label = date.format(DateTimeFormatter.ofPattern(stringResource(id = R.string.notice_date_format)))
    )
}

@Composable
fun NoticeDepartmentBadge(notice: Notice.KwHome) {
    KwNoticeBadge(leadingIconRes = R.drawable.ic_group, label = notice.department)
}

@Composable
fun NoticeTagBadge(notice: Notice.KwHome) {
    KwNoticeBadge(leadingIconRes = R.drawable.ic_tag, label = notice.tag)
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun KwHomeNoticePreview() {
    val notice = Notice.KwHome(
        id = 0,
        title = "[??????] 2022????????? 1?????? ?????? ?????? ??? ??????????????? ?????? ??????",
        tag = "??????",
        department = "???????????????",
        url = "",
        postedDate = LocalDate.now(),
        modifiedDate = LocalDate.now()
    )
    KwNoticeTheme {
        NoticeCard(
            notice = notice,
            onClickNotice = {},
            bookmarked = true,
            onToggleBookmark = { _, _ -> })
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun SwCentralNoticePreview() {
    val notice = Notice.SwCentral(
        id = 1,
        title = "2022 SW???????????? ?????? ????????? ?????? ?????? ??????",
        url = "",
        postedDate = LocalDate.now()
    )
    KwNoticeTheme(useDarkTheme = true) {
        NoticeCard(
            notice = notice,
            onClickNotice = {},
            bookmarked = false,
            onToggleBookmark = { _, _ -> })
    }
}