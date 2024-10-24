@import gg.jte.Content
@import hexlet.code.NamedRoutes
@import hexlet.code.dto.UrlCheckPage
@param UrlCheckPage page
@param Content content

@if(page != null && page.getFlash() != null)
    <div class="alert alert-success" role="alert">
        <p>${page.getFlash()}</p>
    </div>
@endif

@template.layout.page(
    content = @`
    <h1>Сайт: ${page.getNameUrl()}</h1>
    <table>
        <tr>
            <th>ID            </th>
            <th>Название сайта</th>
            <th>Дата создания </th>
        </tr>
        <tr>
            <th>${page.getIdUrl()}</th>
            <th>${page.getNameUrl()}</th>
     <th>${String.valueOf(page.getCreatedUrl().toLocalDateTime().withSecond(0).withNano(0)).replaceFirst("T"," ")}</th>
        </tr>
    </table>    
    <h1>Проверки</h1>
    <form action="${NamedRoutes.urlCheck(page.getIdUrl())}" method="post">
        <input type="submit" value="Запустить проверку">
    </form>
    @if(page.getUrlCheck().isEmpty())
        <p>Пока не было ни одной проверки</p>
    @else
        <table>
            <tr>
                <th>ID</th>
                <th>Код ответа</th>
                <th>title</th>
                <th>h1</th>
                <th>Description</th>
                <th>Дата проверки</th>
            </tr>
        </table>
        @for(var check:page.getUrlCheck())
            <table>
                <tr>
                    <th>${check.getId()}</th>
                    <th>${check.getStatusCode()}</th>
                    <th>${check.getTitle()}</th>
                    <th>${check.getH1()}</th>
                    <th>${String.valueOf(check.getDescription())}</th>
     <th>${String.valueOf(check.getCreatedAt().toLocalDateTime().withSecond(0).withNano(0)).replaceFirst("T", " ")}</th>
                </tr>
            </table>
        @endfor
    @endif
    `,
    footer = @`
        <p>Thank for visiting</p>
    `
)