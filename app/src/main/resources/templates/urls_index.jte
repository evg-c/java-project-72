@import hexlet.code.dto.UrlsPage

@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`
        @if(page.getUrls().isEmpty())
            <p>Пока не добавлено ни одной страницы</p>
        @else
            <tr>
                <th>ID</th>
                <th>URL</th>
            </tr>
            @for(var url: page.getUrls())
                <table>
                    <tr>
                        <td>
                            ${url.getId()}
                        </td>
                        <td>
                            <a href="urls/${url.getId()}">${url.getName()}</a>
                        </td>
                    </tr>
                </table>
            @endfor
        @endif
    `,
    footer = @`
        <p>Thank for visiting</p>
    `
)

